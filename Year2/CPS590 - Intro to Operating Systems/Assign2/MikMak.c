//compiled with -lpthread
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/stat.h>

struct my_msgbuf { //struct to hold a mak/message for queue
    long mtype;
    int vote;
    int error;
    int makLen;
    char mtext[52];
};

struct my_msgbuf buf;
int msqid;
key_t key;
char command;
sem_t* sync_sem;
sem_t* sync_cmd;

int main(void)
{

    if ((key = ftok("MikMakServer.c", 'B')) == -1) {
        perror("ftok");
        exit(1);
    }

    if ((msqid = msgget(key, 0644)) == -1) {
        perror("msgget");
        exit(1);
    }
    
    if ((sync_sem = sem_open("/sync_sem", O_CREAT, S_IRWXU, 0)) == SEM_FAILED) {
	perror("sem_open");
	exit(1);
    }

    if ((sync_cmd = sem_open("/sync_cmd", O_CREAT, S_IRWXU, 0)) == SEM_FAILED) {
	perror("sem_open");
	exit(1);
    }

    printf("Enter lines of text\n");

    buf.mtype = 1; //we don't really care in this case 


	printf("Welcome to MikMak, please enter a command\n");
	printf("[R]efresh, [S]end <mak>, [U]pvote <n>, [D]ownvote <n>, [E]xit: ");
	
    while(fgets(buf.mtext, sizeof(buf), stdin) != NULL) {
        int len = strlen(buf.mtext);
	command = buf.mtext[0];
        /* ditch newline at end, if it exists */
        if (buf.mtext[len-1] == '\n') 
		buf.mtext[len-1] = '\0';

        if (msgsnd(msqid, &buf, sizeof(buf), 0) == -1) // +1 for '\0' 
            perror("msgsnd");
	
	sem_post(sync_sem);

	if (command != 'R' && command != 'S' &&
		command != 'U' && command != 'D' && command != 'E') { //user didnt enter valid command
		
		printf("Please enter a valid command");
	}
	else { //otherwise check for errors from msgq
		if (command == 'R') { //refrsh mak list from server
			int numMaks;
			int i;
			printf("Refresh\n");
			system("clear");
			sem_wait(sync_cmd);
			msgrcv(msqid, &buf, sizeof(buf), 0, 0);
			numMaks = atoi(buf.mtext);
			if (numMaks == 0){
				printf("There are no Maks yet!");
			}
			else {
				printf("Number of Maks: %d\n", numMaks);
				printf("#-Vote-Mak\n");				

				for (i = 0 ; i < numMaks; i++) {

					
					sem_wait(sync_cmd);
					msgrcv(msqid, &buf, sizeof(buf), 0, 0);
					if (buf.vote >= 0) {
						if (buf.vote > 0) {
						printf("%d. +%d %s", i + 1, buf.vote, buf.mtext); 
						}
						else {
						printf("%d.  %d %s", i + 1, buf.vote, buf.mtext);
						}
					}
					else {
						printf("%d. %d %s", i + 1, buf.vote, buf.mtext);
					}
					if (i != numMaks - 1) {
						printf("\n");
					}
				}	
			}
			
		}
		
		if (command == 'S') { // recieve msg to check for erro
			sem_wait(sync_cmd);
			msgrcv(msqid, &buf, sizeof(buf), 0, 0);
			if (buf.error < 0) {
				if (buf.error == -1) {
					printf("You did not enter a message to send");
				}
				else {
					printf("Message is too long!");
				}
			}
			else {
				printf("Sent");
			}
		}
		
		if (command == 'U') { //upvote, print error if exists
			sem_wait(sync_cmd);
			msgrcv(msqid, &buf, sizeof(buf), 0, 0);
			if (buf.error == -1) {
				printf("You did not enter a number, or mak number does not exist.");

			}
			else {
				printf("Upvoted!");	
			}
		}

		if (command == 'D') { //downvote print error if exists, deletes mak if reaches -5 votes
			sem_wait(sync_cmd);
			msgrcv(msqid,&buf, sizeof(buf), 0, 0);
			if (buf.error == -1) {
			printf("You did not enter a number, or mak number does not exist.");
			}
			else {
				printf("Downvoted!");
			}
			if (buf.error == -2) {
				printf(" -5 votes reached.. Mak Deleted");
			}
		}

		if (command == 'E') {
			printf("Exiting...\n");
			break;
		}
	}

	printf("\n[R]efresh, [S]end <mak>, [U]pvote <n>, [D]ownvote <n>, [E]xit: ");
    }



    return 0;
}

