package com.quan.example.cas;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeVector<E> {

	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
	private final Integer N_BUCKET = 30;
	private final Integer FIRST_BUCKET_SIZE = 8;
	private AtomicReference<Descriptor<E>> descriptor;

	public LockFreeVector() {
		buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
		buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
		descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0, null));
	}

	static class Descriptor<E> {
		public int size;
		volatile WriteDescriptor<E> writeOp;

		public Descriptor(int size, WriteDescriptor<E> writeop) {
			this.size = size;
			this.writeOp = writeop;
		}

		public void completeWrite() {
			WriteDescriptor<E> tmpOp = writeOp;
			if (tmpOp != null) {
				tmpOp.doIt();
				writeOp = null;
			}
		}
	}

	static class WriteDescriptor<E> {
		public E oldV;
		public E newV;
		public AtomicReferenceArray<E> addr;
		public int addrIndex;

		public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind, E oldV, E newV) {

			this.addr = addr;
			this.addrIndex = addr_ind;
			this.oldV = oldV;
			this.newV = newV;

		}

		public void doIt() {
			addr.compareAndSet(addrIndex, oldV, newV);
		}
	}

	public void push_back(E e) {

		Descriptor<E> desc;
		Descriptor<E> newd;

		do {
			desc = descriptor.get();
			desc.completeWrite();

			int zeroNumFirst = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);
			int pos = desc.size + FIRST_BUCKET_SIZE;
			int zeroNumPos = Integer.numberOfLeadingZeros(pos);
			int bucketInd = zeroNumFirst - zeroNumPos;

			if (buckets.get(bucketInd) == null) {
				int newLen = 2 * buckets.get(bucketInd - 1).length();
				System.out.println("new length is:" + newLen);
				buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<E>(newLen));
			}

			int idx = (0x80000000 >>> zeroNumPos) ^ pos;
			newd = new Descriptor<>(desc.size + 1, new WriteDescriptor<E>(buckets.get(bucketInd), idx, null, e));
		} while (!descriptor.compareAndSet(desc, newd));

		descriptor.get().completeWrite();
	}

	public E get(int index) {
		int pos = index + FIRST_BUCKET_SIZE;
		int zeroNumFirst = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);
		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
		int bucketInd = zeroNumFirst - zeroNumPos;
		int idx = (0x80000000 >>> zeroNumPos) ^ pos;
		return buckets.get(bucketInd).get(idx);
	}

}
