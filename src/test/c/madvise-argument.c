

#include <stdio.h>
#include <sys/mman.h>

int main(void)
{
    printf("MADV_NORMAL: %d\n", MADV_NORMAL);
    printf("MADV_RANDOM: %d\n", MADV_RANDOM);
    printf("MADV_SEQUENTIAL: %d\n", MADV_SEQUENTIAL);
    printf("MADV_WILLNEED: %d\n", MADV_WILLNEED);
    printf("MADV_DONTNEED: %d\n", MADV_DONTNEED);
    printf("MADV_REMOVE: %d\n", MADV_REMOVE);
    printf("MADV_DONTFORK: %d\n", MADV_DONTFORK);
    printf("MADV_DOFORK: %d\n", MADV_DOFORK);
    printf("MADV_HWPOISON: %d\n", MADV_HWPOISON);
    printf("MADV_MERGEABLE: %d\n", MADV_MERGEABLE);
    printf("MADV_UNMERGEABLE: %d\n", MADV_UNMERGEABLE);
    //printf("MADV_SOFT_OFFLINE: %d\n", MADV_SOFT_OFFLINE);
    printf("MADV_SOFT_OFFLINE: %d\n", 101);
    printf("MADV_HUGEPAGE: %d\n", MADV_HUGEPAGE);
    printf("MADV_NOHUGEPAGE: %d\n", MADV_NOHUGEPAGE);
    printf("MADV_DONTDUMP: %d\n", MADV_DONTDUMP);
    printf("MADV_DODUMP: %d\n", MADV_DODUMP);
    printf("MADV_FREE: %d\n", MADV_FREE);
    return 0;
}
