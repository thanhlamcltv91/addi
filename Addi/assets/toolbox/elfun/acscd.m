## -*- texinfo -*-
## @deftypefn {Function File} {} acscd (@var{x})
## Compute the inverse cosecant in degrees for each element of @var{x}.
## @seealso{cscd, acsc}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = acscd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = acsc(x) .* 180 ./ pi;
endfunction

%!error(acscd())
%!error(acscd(1,2))
%!assert(acscd(0:10:90),180/pi*acsc(0:10:90),-10*eps)
