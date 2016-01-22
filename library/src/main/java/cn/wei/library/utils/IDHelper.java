package cn.wei.library.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class IDHelper {
	static final String HEX_VALUES = "0123456789abcdef";
	
	/**
	 * Returns a hex representation of a byte [] id
	 * 
	 * @param Id Id as byte array.
	 * @return Hex representation of the id (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
	 */
	public static String toString(byte [] id) {
		if(id==null)
			throw new IllegalArgumentException("ID cannot be null");
		if (id.length != 16)
			throw new IllegalArgumentException("ID has a illegal length ("+id.length+")");
		
	    StringBuilder res = new StringBuilder(2 * id.length);

	    for(int i=0; i<4; i++) {
	    	res.append(HEX_VALUES.charAt((id[i] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[i] & 0x0F)));
	    }
	    res.append("-");
	    res.append(HEX_VALUES.charAt((id[4] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[4] & 0x0F)));
	    res.append(HEX_VALUES.charAt((id[5] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[5] & 0x0F)));
	    res.append("-");
	    res.append(HEX_VALUES.charAt((id[6] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[6] & 0x0F)));
	    res.append(HEX_VALUES.charAt((id[7] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[7] & 0x0F)));
	    res.append("-");
	    res.append(HEX_VALUES.charAt((id[8] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[8] & 0x0F)));
	    res.append(HEX_VALUES.charAt((id[9] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[9] & 0x0F)));
	    res.append("-");
	    for(int i=10; i<16; i++) {
	    	res.append(HEX_VALUES.charAt((id[i] & 0xF0) >> 4)).append(HEX_VALUES.charAt((id[i] & 0x0F)));
	    }

	    return res.toString();
	}
	
	public static byte [] fromString(String idHex) {
		if(idHex == null)
			throw new IllegalArgumentException("ID cannot be null");
		
		if ("".equals(idHex))
			return null;
		
		idHex = idHex.replace("-", "");
		if (idHex.length() != 32)
			throw new IllegalArgumentException("ID has a illegal length ("+idHex+")");
		
		byte [] id = new byte[16];
		for(int i=0; i<id.length; i++) {
			id[i] = (byte)Integer.parseInt(idHex.charAt(i*2) + "" + idHex.charAt(i*2+1), 16);
		}
		
		return id;
	}

	/**
	 * Generates a new id for sync objects
	 * 
	 * @return random id
	 */
	public static String generateNew() {
		UUID uuid = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return new String(bb.array());
	}
	
	/**
	 * Compares two ids if they are equal.
	 * 
	 * @param id1 First id.
	 * @param id2 Id to compare with.
	 * @return Returns true if the ids are equals.
	 */
	public static boolean equal(byte [] id1, byte [] id2) {
		return Arrays.equals(id1, id2);
	}
}
