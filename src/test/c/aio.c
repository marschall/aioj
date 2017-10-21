
#include <inttypes.h>

#include <unistd.h>
#include <fcntl.h>

#include <stdlib.h>           /* for malloc() */

#include <stdio.h>            /* for perror() */
#include <unistd.h>           /* for syscall() */
#include <sys/syscall.h>      /* for __NR_* definitions */
#include <linux/aio_abi.h>    /* for AIO types and constants */

static inline int io_setup(unsigned nr, aio_context_t *ctxp)
{
	return syscall(__NR_io_setup, nr, ctxp);
}

static inline int io_destroy(aio_context_t ctx) 
{
	return syscall(__NR_io_destroy, ctx);
}

static inline int io_submit(aio_context_t ctx, long nr, struct iocb **iocbpp)
{
	return syscall(__NR_io_submit, ctx, nr, iocbpp);
}

static inline int io_getevents(aio_context_t ctx, long min_nr, long max_nr,
		struct io_event *events, struct timespec *timeout)
{
	return syscall(__NR_io_getevents, ctx, min_nr, max_nr, events, timeout);
}


int main(void)
{
  aio_context_t *ctx;
  int ret;
  
  ctx = malloc(sizeof(aio_context_t));
  ret = io_setup(128, ctx);
  if (ret < 0) {
    perror("io_setup error");
    return -1;
  }
  
  ret = io_destroy(*ctx);
  if (ret < 0) {
    perror("io_destroy error");
    return -1;
  }
  free(ctx);
  
  return 0;
}

