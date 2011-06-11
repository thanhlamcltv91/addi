## -*- texinfo -*-
## @deftypefn {Function File} {} acosd (@var{x})
## Compute the inverse cosine in degrees for each element of @var{x}.
## @seealso{cosd, acos}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = acosd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = acos(x) .* 180 ./ pi;
endfunction

%!error(acosd())
%!error(acosd(1,2))
%!assert(acosd(0:0.1:1),180/pi*acos(0:0.1:1),-10*eps)
