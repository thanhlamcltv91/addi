## -*- texinfo -*-
## @deftypefn {Function File} {} record (@var{sec}, @var{sampling_rate})
## Records @var{sec} seconds of audio input into the vector @var{x}.  The
## default value for @var{sampling_rate} is 8000 samples per second, or
## 8kHz.  The program waits until the user types @key{RET} and then
## immediately starts to record.
## @seealso{lin2mu, mu2lin, loadaudio, saveaudio, playaudio, setaudio}
## @end deftypefn

## Author: AW <Andreas.Weingessel@ci.tuwien.ac.at>
## Created: 19 September 1994
## Adapted-By: jwe

function X = record (sec, sampling_rate)

  if (nargin == 1)
    sampling_rate = 8000;
  elseif (nargin != 2)
    print_usage ();
  endif

  unwind_protect

    file = tmpnam ();

    input ("Please hit ENTER and speak afterwards!\n", 1);

    cmd = sprintf ("dd if=/dev/dsp of=\"%s\" bs=%d count=%d",
                   file, sampling_rate, sec)

    system (cmd);

    num = fopen (file, "rb");

    [Y, c] = fread (num, sampling_rate * sec, "uchar");

    fclose (num);

  unwind_protect_cleanup

    unlink (file);

  end_unwind_protect

  X = Y - 127;

endfunction
