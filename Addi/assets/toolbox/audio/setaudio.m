## -*- texinfo -*-
## @deftypefn {Function File} {} setaudio ([@var{w_type} [, @var{value}]])
## Execute the shell command @samp{mixer [@var{w_type} [, @var{value}]]}
## @end deftypefn

## Author: AW <Andreas.Weingessel@ci.tuwien.ac.at>
## Created: 5 October 1994
## Adapted-By: jwe

function setaudio (w_type, value)

  if (nargin == 0)
    system ("mixer");
  elseif (nargin == 1)
    system (sprintf ("mixer %s", w_type));
  elseif (nargin == 2)
    system (sprintf ("mixer %s %d", w_type, value));
  else
    print_usage ();
  endif

endfunction
