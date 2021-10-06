# About Bloom Filter resolution

1. Created a BitSet to store position of created hash with pre defined size;
2. Added function to create hash using a cryptographic hash function MD-5 - 128 bits;
3. The generate hash function is based from array of bytes of declared words (in txt file), then it's splited in four int bytes and performs a bitwise AND operation by 0xff. 
4. Added function to compare if added word exists in BitSet from word as parameter. 
