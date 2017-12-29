#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(void)
{
    printf("SEEK_SET: %d\n", SEEK_SET);
    printf("SEEK_CUR: %d\n", SEEK_CUR);
    printf("SEEK_END: %d\n", SEEK_END);

    printf("SEEK_DATA: %d\n", SEEK_DATA);
    printf("SEEK_HOLE: %d\n", SEEK_HOLE);
    return 0;
}
