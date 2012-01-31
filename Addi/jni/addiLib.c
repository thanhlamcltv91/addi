#include <fftw3.h>
#include "addiLib.h"

JNIEXPORT jint JNICALL Java_com_addi_toolbox_general_fft_fftNative (JNIEnv * env, jobject thiz) {
  
	unsigned int N = 20;
	fftw_complex *in, *out;
	fftw_plan p;
	in = (fftw_complex*) fftw_malloc(sizeof(fftw_complex) * N);
	out = (fftw_complex*) fftw_malloc(sizeof(fftw_complex) * N);
	p = fftw_plan_dft_1d(N, in, out, FFTW_FORWARD, FFTW_ESTIMATE);
	fftw_execute(p); /* repeat as needed */
	fftw_destroy_plan(p);
	fftw_free(in); fftw_free(out);
	return 1;
}
