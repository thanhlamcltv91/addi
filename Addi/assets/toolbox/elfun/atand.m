## -*- texinfo -*-
## @deftypefn {Function File} {} atand (@var{x})
## Compute the inverse tangent in degrees for each element of @var{x}.
## @seealso{tand, atan}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = atand (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = 180 ./ pi .* atan (x);
endfunction

%!error(atand())
%!error(atand(1,2))
%!assert(atand(0:10:90),180./pi.*atan(0:10:90),-10*eps)
