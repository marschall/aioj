
#include <errno.h>
#include <string.h>
#include <stdio.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(void)
{
    printf("sizeof(int): %ld\n", sizeof(int));
    printf("sizeof(long): %ld\n", sizeof(long));
    printf("sizeof(mode_t): %ld\n", sizeof(mode_t));
    printf("sizeof(off_t): %ld\n", sizeof(off_t));
    printf("sizeof(size_t): %ld\n", sizeof(size_t));
    printf("sizeof(time_t): %ld\n", sizeof(time_t));
    return 0;
}
