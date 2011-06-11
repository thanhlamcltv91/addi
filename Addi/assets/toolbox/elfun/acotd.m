## -*- texinfo -*-
## @deftypefn {Function File} {} acotd (@var{x})
## Compute the inverse cotangent in degrees for each element of @var{x}.
## @seealso{cotd, acot}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = acotd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = atand (1 ./ x);
endfunction

%!error(acotd())
%!error(acotd(1,2))
%!assert(acotd(0:10:90),180./pi.*acot(0:10:90),-10*eps)
