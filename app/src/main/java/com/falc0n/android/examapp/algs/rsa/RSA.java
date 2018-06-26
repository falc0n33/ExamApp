package com.falc0n.android.examapp.algs.rsa;

public class RSA {
	private long p, q;
	private long n, fi;
	private long publicKey, privateKey;
	private Pair privateKeyLog;
	
	public RSA (long p, long q, long e) {
		this.p = p;
		this.q = q;
		this.publicKey = e;
		
		n = p * q;
		fi = (p - 1) * (q - 1);
		
		privateKeyLog = Extra.solveWithEuler(e, 1, fi);
		this.privateKey = Long.parseLong(privateKeyLog.getV().toString());
	}
	
	public Pair encrypt(long message) {
		StringBuilder sb = new StringBuilder();
		sb.append("Message = " + message + "\n");
		sb.append("C = M^e mod n\n");
		sb.append("C = " + message + "^" + publicKey + " mod " + n + "\n");
		
		long encrypted = Long.parseLong(Extra.fastMultiply(message, publicKey, n));
		sb.append("C = " + encrypted + "\n");
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(encrypted);
		
		return res;
	}
	
	public Pair decrypt(long encrypted) {
		StringBuilder sb = new StringBuilder();
		sb.append("Encrypted = " + encrypted + "\n");
		sb.append("M = C^d mod n\n");
		sb.append("M = " + encrypted + "^" + privateKey + " mod " + n + "\n");
		
		long decrypted = Long.parseLong(Extra.fastMultiply(encrypted, privateKey, n));
		sb.append("M = " + decrypted + "\n");
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(decrypted);
		
		return res;
	}
	
	public Pair getSignature(long message) {
		StringBuilder sb = new StringBuilder();
		sb.append("Message = " + message + "\n");
		sb.append("S = M^d mod n\n");
		sb.append("S = " + message + "^" + privateKey + " mod " + n + "\n");
		
		long signature = Long.parseLong(Extra.fastMultiply(message, privateKey, n));
		sb.append("S = " + signature + "\n");
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(signature);
		
		return res;
	}
	
	public Pair verifySignature(long message, long signature) {
		StringBuilder sb = new StringBuilder();
		sb.append("Message = " + message + "\n");
		sb.append("Signature = " + signature + "\n");	
		sb.append("M' = S^e mod n\n");
		sb.append("M' = " + signature + "^" + publicKey + " mod " + n + "\n");
		
		
		long mc = Long.parseLong(Extra.fastMultiply(signature, publicKey, n));
		sb.append("M' = " + mc + "\n");	
		
		boolean verify = (mc == message);
		if (verify) {
			sb.append("M = M'\n");
		} else {
			sb.append("M != M'\n");
		}
		
		Pair res = new Pair();
		res.setU(sb.toString());
		res.setV(verify);
		
		return res;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("p = " + p + "\n");
		sb.append("q = " + q + "\n");
		sb.append("n = " + n + "\n");
		sb.append("fi(n) = " + fi + "\n");
		sb.append("e = " + publicKey + "\n");
		sb.append("d = " + privateKey + "\n");
		sb.append("\n" + privateKeyLog.getU() + "\n");
		
		return sb.toString();
	}
}
