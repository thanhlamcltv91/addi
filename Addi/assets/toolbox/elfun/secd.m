## -*- texinfo -*-
## @deftypefn {Function File} {} secd (@var{x})
## Compute the secant for each element of @var{x} in degrees.
## @seealso{asecd, sec}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = secd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = 1 ./ cosd (x);
endfunction

%!error(secd())
%!error(secd(1,2))
%!assert(secd(0:10:80),sec(pi*[0:10:80]/180),-10*eps)
%!assert(secd([0,180,360]) != Inf)
%!assert(secd([90,270]) == Inf)
