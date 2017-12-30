#include <stdio.h>
#include <fcntl.h>

int main(void)
{
    printf("POSIX_FADV_NORMAL: %d\n", POSIX_FADV_NORMAL);
    printf("POSIX_FADV_SEQUENTIAL: %d\n", POSIX_FADV_SEQUENTIAL);
    printf("POSIX_FADV_RANDOM: %d\n", POSIX_FADV_RANDOM);
    printf("POSIX_FADV_NOREUSE: %d\n", POSIX_FADV_NOREUSE);
    printf("POSIX_FADV_WILLNEED: %d\n", POSIX_FADV_WILLNEED);
    printf("POSIX_FADV_DONTNEED: %d\n", POSIX_FADV_DONTNEED);
    return 0;
}
