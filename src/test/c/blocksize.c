
#include <errno.h>
#include <string.h>
#include <stdio.h>
#include <sys/ioctl.h>
#include <linux/fs.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>


int main(void)
{
    int fd;
    int sector_size;
    // fd = open("/home/marschall/Documents/workspaces/default/aioj/src/test/c/blocksize.c", O_RDONLY);
    // fd = open("/", O_RDONLY);
    fd = open("/dev/sdb", O_RDONLY);
    if (fd == -1)
    {
      perror("FD Error: ");
      return(-1);
    }
    int result = ioctl(fd, BLKPBSZGET, &sector_size);
    if (result == 0)
    {
        // http://man7.org/linux/man-pages/man3/strerror.3.html
        // int errnum = errno;
        // char *strerror_r(errnum, char *buf, size_t buflen)
        printf("%d\n", sector_size);
        return 0;
    }
    else
    {
        printf("ioctl failed\n");
        perror("");
        return result;
    }
}
