//compiled with -lpthread
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sched.h>
#include <unistd.h>
#define MAX_MAKS 9

struct my_msgbuf {
    long mtype;
    int vote;
    int error;
    int makLen;
    char mtext[52];
};

void refresh();
void newMak();
void upVote();
void downVote();
void delete(int n);
int check();


int totalMaks;
char command;
struct my_msgbuf maks[MAX_MAKS];
struct my_msgbuf buf;
int msqid;
key_t key;
sem_t* sync_cmd;
sem_t* sync_sem;

int cleanUp() { //when 'X' is entered in server, close the server
	while (command != 'X') {
		scanf("%c", &command);
	}

	if (msgctl(msqid, IPC_RMID, NULL) == -1) {
		perror("msgctl");
		exit(1);
	}

	if (sem_close(sync_sem) == -1) {
		perror("sem_close");
		exit(1);
	}

	if (sem_unlink("sync_sem") == -1) {
		perror("sem_unlink");
		exit(1);
	}

	if (sem_close(sync_cmd) == -1) {
		perror("sem_close");
		exit(1);
	}

	if (sem_unlink("sync_cmd") == -1) {
		perror("sem_unlink");
		exit(1);
	}
	
	printf("Clean up complete\n");

	kill (getppid(), 13);
}

int main(void) {

    /* 
	create thread that waits for command to close server
    */
    const int STACK_SIZE = 65336;
    char *stack;
    char *stackTop;
    stack = malloc(STACK_SIZE);

    if (stack == NULL) {
	perror("malloc");
	exit(1);
    }

    stackTop = stack + STACK_SIZE;

    if ((clone(cleanUp, stackTop, CLONE_VM, NULL)) == -1) {
	perror("clone");
	exit(1);
    }

    totalMaks = 0;
    buf.mtype = 1;
    if ((key = ftok("MikMakServer.c", 'B')) == -1) {  
        perror("ftok");
        exit(1);
    }

    if ((msqid = msgget(key, 0644 | IPC_CREAT)) == -1) { /* connect to the queue */
        perror("msgget");
        exit(1);
    }

    //open named semaphores
    if ((sync_sem = sem_open("/sync_sem", O_CREAT, S_IRWXU, 0)) == SEM_FAILED) {
	perror("sem_open");
	exit(1);
    }
    
    if ((sync_cmd = sem_open("/sync_cmd", O_CREAT, S_IRWXU, 0)) == SEM_FAILED) {
	perror("sem_open");
	exit(1);
    }

    printf("Ready to receive messages.\n");

    for(;;) { //loop infinitly

	printf("Waiting for command\n");
	sem_wait(sync_sem);
        if (msgrcv(msqid, &buf, sizeof(buf), 0, 0) == -1) {
            perror("msgrcv");
            exit(1);
        }

	buf.mtype = 1;	

	command = buf.mtext[0];
	


	if (command == 'R') { //refresh command
		printf("Refresh\n");
		refresh();

	}

	if (command == 'S') {
		printf("Send\n");
		printf("Text Size %d\n", strlen(buf.mtext));
		if (strlen(buf.mtext) <= 2 || strlen(buf.mtext) > 52) {
			if (strlen(buf.mtext) <= 2) {
				buf.error = -1;
				printf("Error no message\n");
			}
			else {
				buf.error = -2;
				printf("Error message too long\n");
			}
		}
		else {
			buf.error = 0;
			newMak();
		}
		
		msgsnd(msqid, &buf, sizeof(buf), 0);
		sem_post(sync_cmd);
	}

	if (command == 'U') {
		upVote();
		printf("Upvoted!\n");
	}

	if (command == 'D') {
		downVote();
		printf("Downvoted!\n");
	}
	
	printf("Command complete\n");
    }
    return 0;
}

void refresh() { //refresh mak list for client
	int i;
	int j;
	int len;
	struct my_msgbuf buftmp;
	buftmp.mtype = 1;
	snprintf(buf.mtext, sizeof(buf.mtext), "%d", totalMaks);
	printf("# of Maks %s \n", buf.mtext);
	msgsnd(msqid, &buf, sizeof(buf), 0);
	
	sem_post(sync_cmd);
	
	if (totalMaks != 0) {		
		for (i = 0 ; i < totalMaks ; i++) {
			buftmp.makLen = maks[i].makLen;
			len = maks[i].makLen;
			for (j = 0 ; j < len ; j++) {
				buftmp.mtext[j] = maks[i].mtext[j];
			}
			
			for (j = len ; j < 50 ; j++) {
				buftmp.mtext[j] = '\0';
			}

			buftmp.vote = maks[i].vote;
			printf("Sending - Vote: %d, Mak: %s , MakSize: %d\n", buftmp.vote, buftmp.mtext, buftmp.makLen);
			msgsnd(msqid, &buftmp, sizeof(buftmp), 0);
		
			sem_post(sync_cmd);
	
		}
	}
	
}

void newMak() {
	//reallocate memory for array
	//increment total maks
	//make a make and set vote = 0
	//add mak to the array
	int i;	
	int j = 0;
	//if there are already max maks ..
	//delete the first mak
	if (totalMaks == MAX_MAKS) { 
		delete(0);
	}
	maks[totalMaks].vote = 0;
	maks[totalMaks].makLen = strlen(buf.mtext) - 2;
	for (i = 2 ; i < strlen(buf.mtext) ; i++) {
		maks[totalMaks].mtext[j] = buf.mtext[i];
		j++;
	}
	printf("Vote: %d, Mak: %s, MakSize: %d\n", maks[totalMaks].vote, maks[totalMaks].mtext, maks[totalMaks].makLen); 
	totalMaks++;
}

void upVote() { //upvote mak
	if (check() == -1) {
		//return error for client to pick up
		buf.error = -1;
		msgsnd(msqid, &buf, sizeof(buf), 0);
		sem_post(sync_cmd);
	}
	
	else {
		int makNum = (buf.mtext[2] - '0') - 1;
		buf.error = 0;
		maks[makNum].vote++;
		printf("Upvoting - Vote: %d, Mak: %s\n", maks[makNum].vote, maks[makNum].mtext);
		msgsnd(msqid, &buf, sizeof(buf), 0);
		sem_post(sync_cmd);
		//return to client meaning no errors
	}
}

void downVote() { //downvote a mak
	//char makNum = buf.mtext[3];

	if (check() == -1) {
		//return error to client
		buf.error = -1;
		msgsnd(msqid, &buf, sizeof(buf), 0);
		sem_post(sync_cmd);
	}

	else {
		//decrement vote
		//if vote < -5, delete mak and shift over array
		//return 0 for no errors
		buf.error = 0;
		int makNum = (buf.mtext[2] - '0') - 1;
		maks[makNum].vote--;
		if (maks[makNum].vote <= -5) {
			if (makNum == MAX_MAKS - 1) {
				totalMaks--;
			}
			else {
				delete(makNum);
			}
			buf.error = -2;
			
		}

		msgsnd(msqid, &buf, sizeof(buf), 0);
		sem_post(sync_cmd);
	}
}

int check() { //check if user entered appropriate mak number
	int makNum = buf.mtext[2] - '0';
	
	if (makNum <= totalMaks && makNum > 0) {
		printf("Check good\n");
		return 0;
	}
	else {
		printf("Check bad\n");
		return -1;
	}

}

void delete(int n) {
	//delete mak if vote is < 5 (checked from downVote())
	//delete mak when list reachesmax maks
	int i;
	int j;
	int k;
	totalMaks--;
	for (i = n ; i < totalMaks ; i++) { //shift maks 
		maks[i] = maks[i + 1];
		maks[i].vote = maks[i + 1].vote;
		maks[i].makLen = maks[i + 1].makLen;
		for (j = 0 ; j < maks[i + 1].makLen ; j++) {
			maks[i].mtext[j] = maks[i + 1].mtext[j];
		}
	}
}	
