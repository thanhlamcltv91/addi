## -*- texinfo -*-
## @deftypefn {Function File} {} cotd (@var{x})
## Compute the cotangent for each element of @var{x} in degrees.
## @seealso{acotd, cot}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = cotd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = 1 ./ tand (x);
endfunction

%!error(cotd())
%!error(cotd(1,2))
%!assert(cotd(10:10:80),cot(pi*[10:10:80]/180),-10*eps)
%!assert(cotd([0,180,360]) == Inf)
%!assert(cotd([90,270]) == 0)
