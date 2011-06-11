## -*- texinfo -*-
## @deftypefn {Function File} {} cscd (@var{x})
## Compute the cosecant for each element of @var{x} in degrees.
## @seealso{acscd, csc}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = cscd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = 1 ./ sind (x);
endfunction

%!error(cscd())
%!error(cscd(1,2))
%!assert(cscd(10:10:90),csc(pi*[10:10:90]/180),-10*eps)
%!assert(cscd([0,180,360]) == Inf)
%!assert(cscd([90,270]) != Inf)

