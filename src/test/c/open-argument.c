#define _GNU_SOURCE

#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(void)
{
    printf("O_RDONLY: %d\n", O_RDONLY);
    printf("O_WRONLY: %d\n", O_WRONLY);
    printf("O_RDWR: %d\n", O_RDWR);

    printf("O_APPEND: %d\n", O_APPEND);
    printf("O_ASYNC: %d\n", O_ASYNC);
    printf("O_CLOEXEC: %d\n", O_CLOEXEC);
    printf("O_CREAT: %d\n", O_CREAT);
    printf("O_DIRECT: %d\n", O_DIRECT);
    printf("O_DIRECTORY: %d\n", O_DIRECTORY);
    printf("O_DSYNC: %d\n", O_DSYNC);
    printf("O_EXCL: %d\n", O_EXCL);
    printf("O_LARGEFILE: %d\n", O_LARGEFILE);
    printf("O_NOATIME: %d\n", O_NOATIME);
    printf("O_NOCTTY: %d\n", O_NOCTTY);
    printf("O_NOFOLLOW: %d\n", O_NOFOLLOW);
    printf("O_NONBLOCK: %d\n", O_NONBLOCK);
    printf("O_NDELAY: %d\n", O_NDELAY);
    printf("O_PATH: %d\n", O_PATH);
    printf("O_SYNC: %d\n", O_SYNC);
    printf("O_TMPFILE: %d\n", O_TMPFILE);
    printf("O_TRUNC: %d\n", O_TRUNC);
    
    printf("S_IRWXU: %d\n", S_IRWXU);
    printf("S_IRUSR: %d\n", S_IRUSR);
    printf("S_IWUSR: %d\n", S_IWUSR);
    printf("S_IXUSR: %d\n", S_IXUSR);
    printf("S_IRWXG: %d\n", S_IRWXG);
    printf("S_IRGRP: %d\n", S_IRGRP);
    printf("S_IWGRP: %d\n", S_IWGRP);
    printf("S_IXGRP: %d\n", S_IXGRP);
    printf("S_IRWXO: %d\n", S_IRWXO);
    printf("S_IROTH: %d\n", S_IROTH);
    printf("S_IWOTH: %d\n", S_IWOTH);
    printf("S_IXOTH: %d\n", S_IXOTH);
    printf("S_ISUID: %d\n", S_ISUID);
    printf("S_ISGID: %d\n", S_ISGID);
    printf("S_ISVTX: %d\n", S_ISVTX);
    return 0;
}
