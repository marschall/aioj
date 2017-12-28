
#include <errno.h>
#include <string.h>
#include <stdio.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(void)
{
    printf("sizeof(blkcnt_t): %ld\n", sizeof(blkcnt_t));
    printf("sizeof(blksize_t): %ld\n", sizeof(blksize_t));
    printf("sizeof(dev_t): %ld\n", sizeof(dev_t));
    printf("sizeof(gid_t): %ld\n", sizeof(gid_t));
    printf("sizeof(int): %ld\n", sizeof(int));
    printf("sizeof(ino_t): %ld\n", sizeof(ino_t));
    printf("sizeof(long): %ld\n", sizeof(long));
    printf("sizeof(mode_t): %ld\n", sizeof(mode_t));
    printf("sizeof(nlink_t): %ld\n", sizeof(nlink_t));
    printf("sizeof(off_t): %ld\n", sizeof(off_t));
    printf("sizeof(size_t): %ld\n", sizeof(size_t));
    printf("sizeof(time_t): %ld\n", sizeof(time_t));
    printf("sizeof(uid_t): %ld\n", sizeof(uid_t));
    return 0;
}
