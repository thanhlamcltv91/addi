## -*- texinfo -*-
## @deftypefn {Function File} {} saveaudio (@var{name}, @var{x}, @var{ext}, @var{bps})
## Saves a vector @var{x} of audio data to the file
## @file{@var{name}.@var{ext}}.  The optional parameters @var{ext} and
## @var{bps} determine the encoding and the number of bits per sample used
## in the audio file (see @code{loadaudio});  defaults are @file{lin} and
## 8, respectively.
## @seealso{lin2mu, mu2lin, loadaudio, playaudio, setaudio, record}
## @end deftypefn

## Author: AW <Andreas.Weingessel@ci.tuwien.ac.at>
## Created: 5 September 1994
## Adapted-By: jwe

function saveaudio (name, X, ext, bit)

  if (nargin < 2 || nargin > 4)
    print_usage ();
  endif

  if (nargin == 2)
    ext = "lin";
  endif

  if (nargin < 4)
    bit = 8;
  elseif (bit != 8 && bit != 16)
    error ("saveaudio: bit must be either 8 or 16");
  endif

  [nr, nc] = size (X);
  if (nc != 1)
    if (nr == 1)
      X = X';
      nr = nc;
    else
      error ("saveaudio: X must be a vector");
    endif
  endif

  num = fopen ([name, ".", ext], "wb");

  if (strcmp (ext, "lin") || strcmp (ext, "raw"))
    if (bit == 8)
      ld = max (abs (X));
      if (ld > 127)   # convert 16 to 8 bit
        if (ld < 16384)
          sc = 64 / ld;
        else
          sc = 1 / 256;
        endif
        X = fix (X * sc);
      endif
      X = X + 127;
      c = fwrite (num, X, "uchar");
    else
      c = fwrite (num, X, "short");
    endif
  elseif (strcmp (ext, "mu") || strcmp (ext, "au")
	  || strcmp (ext, "snd") || strcmp (ext, "ul"))
    Y = lin2mu (X);
    c = fwrite (num, Y, "uchar");
  else
    fclose (num);
    error ("saveaudio does not support given extension");
  endif

  fclose (num);

endfunction
