/*
 * This file includes the native code used to implement methods for
 * the File, InputFile and OutputFile classes. Specifically, classes
 * InputFile and OutputFile have native methods that interact with
 * the underlying system.
 *
 * The prototype for the non-static functions are found in the JAVAH
 * generated header file for the class. Also, the JAVAH generated
 * stubfile takes care of marshalling the function parameters from
 * the JAVA world into the C world.
 *
 * First off, we need to include the JAVA internal header files
 * for macros and function prototypes required to manipulate JAVA
 * data structures and functions.
 *
 * StubPreamble.h includes the structure and macro definitions needed
 * to convert JAVA data structures into C data structures. For
 * example, macros such as "unhand" are defined in StubPreamble.h.
 *
 * javaString.h defines the JAVA string maniuplation macros and
 * routines needed to convert between JAVA and C strings.
 */
#include <StubPreamble.h>
#include <javaString.h>


/*
 * These headers are special in that they are generated by JAVAH. These
 * include the C structure definitions for the JAVA classes.
 */
#include "InputFile.h"
#include "OutputFile.h"


/*
 * These are some standard Unix headers we use to implement our system
 * dependent native methods.
 */
#include <sys/types.h>
#include <sys/param.h>
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>


/*
 * Here we define constants used by this module. In our case we need
 * to define our local path separator, because it is different from the
 * one used in JAVA. Note that JAVA's version path separator is
 * defined in the JAVAH generated header files.
 */
#define	LOCAL_PATH_SEPARATOR	'/'


/*
 * static void
 * convertPath(char *)
 *
 * This function provides C string path conversions from those
 * separated by the File separator character to the system defined
 * one.
 */
static void
convertPath(char *str)
{
    while (*str != '\0') {
	if ((*str == InputFile_separatorChar) ||
	    (*str == OutputFile_separatorChar)) {
	    *str = LOCAL_PATH_SEPARATOR;
	}
	str++;
    }
    return;
}


/*
 * long
 * InputFile_open(struct HInputFile *)
 *
 * This function implements the native method open in class InputFile
 * This fact is evidenced by the name. The function name is derived by
 * concatenating the package name with the class name with the method name.
 * The semantics for this method are defined in the class file.
 *
 * The parameter to this function can be thought of as a "handle to
 * a InputFile object." The actual makeup of this is the data portion
 * of the class followed by the method table for the class.
 */
long 
InputFile_open(struct HInputFile *this)
{
    int		fd;
    char	buf[MAXPATHLEN];

    /*
     * We need to convert JAVA path string into a C string and
     * then change it to a system dependent path string.
     * Note that "unhand" gets the data portion of the handle to
     * the C representation of the JAVA class InputFile.
     */
    javaString2CString(unhand(this)->path, buf, sizeof(buf));
    convertPath(buf);

    /*
     * Now we actually call the system dependent routines to implement
     * the method. Note that we could throw an exception via
     * SignalError() instead of returning a boolean.
     */
    fd = open(buf, O_RDONLY);
    if (fd < 0)
        return(FALSE);

    /*
     * On success we store the system fd into the class variable for
     * future calls to this library.
     */
    unhand(this)->fd = fd;
    return(TRUE);
}


/*
 * void
 * InputFile_close(struct HInputFile *)
 *
 * Implements the close method for class InputFile.
 */
void 
InputFile_close(struct HInputFile *this)
{
     close(unhand(this)->fd);
     unhand(this)->fd = -1;
     return;
}


/*
 * long 
 * InputFile_read(struct HInputFile *, HArrayOfByte *, long )
 *
 * Implements the read method for class InputFile.
 */
long 
InputFile_read(struct HInputFile *this, 
		    HArrayOfByte *buffer, 
		    long count)
{
    /*
     * We use another macro here called obj_length which returns the
     * length of an JAVA array object. Again, unhand of the Array
     * object gets us the data structure portion of the object.
     */
    char *data	= unhand(buffer)->body;
    int  len	= obj_length(buffer);
    int  actual;

    if (len < count) {
	actual = len;
    }
    else {
	actual = count;
    }
    actual = read(unhand(this)->fd, data, actual);
    if (actual == 0)
	return(-1);
    return(actual);
}


/*
 * long 
 * OutputFile_open(struct HOutputFile *)
 *
 * Implements the open method for class OutputFile.
 * It is virually identical to InputFile_open. Please, refer to the
 * comments for that function for more information.
 */
long 
OutputFile_open(struct HOutputFile *this)
{
    int		fd;
    char	buf[MAXPATHLEN];

    javaString2CString(unhand(this)->path, buf, sizeof(buf));
    convertPath(buf);
    fd = open(buf, O_RDWR|O_CREAT|O_TRUNC, 0644);
    if (fd < 0)
        return(FALSE);
    unhand(this)->fd = fd;
    return(TRUE);
}


/*
 * void 
 * OutputFile_close(struct HOutputFile *)
 *
 * Implements the close method for class OutputFile.
 */
void 
OutputFile_close(struct HOutputFile *this)
{
    close(unhand(this)->fd);
    unhand(this)->fd = -1;
    return;
}


/*
 * long 
 * OutputFile_write(struct HOutputFile *, HArrayOfByte *, long )
 *
 * Implements the write method for class OutputFile.
 */
long 
OutputFile_write(struct HOutputFile *this, 
		      HArrayOfByte *buffer, 
		      long count)
{
    char *data	= unhand(buffer)->body;
    int  len	= obj_length(buffer);
    int  actual;

    if (len < count) {
	actual = len;
    }
    else {
	actual = count;
    }
    actual = write(unhand(this)->fd, data, actual);
    return(actual);
}
