package me.legrange.haveibeenpwned;

/**
 * A hash that may indicate a password was pwned. 
 * @author gideon
 */
public class PwnedHash {
    
    private final String hash;
    private final int count;

    public PwnedHash(String hash, int count) {
        this.hash = hash;
        this.count = count;
    }

    public String getHash() {
        return hash;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return hash + ":" + count;
    }

    
}
