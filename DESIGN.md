direct ByteBuffer

No
 * allocations behind the back (except for iocb and iocb array and events array)
 * no threads behind the back
 * object or buffer pools
 * reflection, Unsafe, only API
 * no copying
 * JNI criticals

Instead
 * user controls buffer (has to pool them, if he wants to)
 * user has to clear buffers
 * user controls objects (has to pool them, if he wants to)
 * user controls threads
 * copying to heap structures (if he wants to)

Limitations
 * Linux 64bit only
  * x32 probably doesn't work
  * everything but x86 probably should work
 
To Test
 * read and write sliced buffer

Not supporting io_cancel for now.
Not supporting vectored io for now.


Direct vs Heap
==============
 * Direct
  * HotSpot goes through Bits.reserveMemory System.gc()​ followed​ ​by​  sleep()
  * can be passed to system call without copy
  * reads are slower than heap ByteBuffer because of range check
  * memory barrier same as Java
  * .get is an instrinsic (custom abstraction has JNI overhead)
  * TODO benchmark JNI overhead
 * Heap
  * can hit slow path of allocator, allocation in old
  * gets at least 16 times to make it into old
  * may get copied in old