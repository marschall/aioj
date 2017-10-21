#include <errno.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>


int main(void)
{
    size_t alignment;
    size_t size;
    void *buf;
    
    alignment = 512;
    size = 4096;
    buf = aligned_alloc(alignment, size);
    if (buf == NULL)
    {
      printf("aligned_alloc failed\n");
      return(-1);
    }
    free(buf);
    return 0;
}