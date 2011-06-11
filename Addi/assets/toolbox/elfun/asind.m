## -*- texinfo -*-
## @deftypefn {Function File} {} asind (@var{x})
## Compute the inverse sine in degrees for each element of @var{x}.
## @seealso{sind, asin}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = asind (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = asin(x) .* 180 ./ pi;
endfunction

%!error(asind())
%!error(asind(1,2))
%!assert(asind(0:0.1:1),180/pi*asin(0:0.1:1),-10*eps)
