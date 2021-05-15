/*
 * Copyright (c) 1994 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
/*
 * This file extends the File class by adding file manipulation
 * methods creating a read-only input file abstraction. We leverage
 * the path manipulation code provided in the File superclass and
 * extend that code with support for opening and reading files.
 */

/**
 * Define class InputFile that presents a simple read-only input file 
 * abstraction. Note that we use native or non-Java methods to
 * implement some of the methods.
 */
public
class InputFile extends File {

    /**
     * Link in the native library that we depend on.  If we cannot
     * link this in, an exception is generated and the class loading
     * fails. We have arbitrarily named the library "file" at the
     * Java level (or libfile.so at the solaris level). Additionally,
     * the System call is part of the static initializer for the class.
     * Thus, the library is loaded as part of this class being loaded.
     */
    static {
	try {
            System.loadLibrary("file");
	} catch (UnsatisfiedLinkError e) {
            System.out.println("can't find your library");
            System.exit(-1);
        }

    }

    /**
     * Holds the system dependent handle to the file resource.
     */
    protected int fd;

    /**
     * Constructor for the input file object. Initializes the
     * parent class with the path name.
     */
    public InputFile(String path) {
	super(path);
    }

    /**
     * Attempts to open the file for reading. Returns
     * TRUE on success and FALSE on failure. Alternatively, we could
     * throw an exception and catch it.
     */
    public native boolean open();

    /**
     * Attempts to close the previously opened file. Has
     * no return value.
     */
    public native void close();

    /**
     * Reads some number of bytes from the opened file. Returns
     * the number of bytes read or -1 when the file is empty.
     */
    public native int read(byte b[], int len);
}
