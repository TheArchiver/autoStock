package com.autoStock.adjust;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.autoStock.Co;
import com.autoStock.tools.StringTools;

/**
 * @author Kevin Kowalewski
 * 
 */
public class PermutationCore {
	private String[] a;
	private int n;

	public PermutationCore(String[] arrayOfPossibilities, int lengthOfPermutation) {
		this.a = arrayOfPossibilities;
		this.n = lengthOfPermutation;
	}

	public String[][] getVariations() {
		int l = a.length;
		int permutations = (int) Math.pow(l, n);
		String[][] table = new String[permutations][n];

		for (int x = 0; x < n; x++) {
			int t2 = (int) Math.pow(l, x);
			for (int p1 = 0; p1 < permutations;) {
				for (int al = 0; al < l; al++) {
					for (int p2 = 0; p2 < t2; p2++) {
						table[p1][x] = a[al];
						p1++;
					}
				}
			}
		}
		
		return table;
	}
}