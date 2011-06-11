## -*- texinfo -*-
## @deftypefn {Function File} {} asecd (@var{x})
## Compute the inverse secant in degrees for each element of @var{x}.
## @seealso{secd, asec}
## @end deftypefn

## Author: David Bateman <dbateman@free.fr>

function y = asecd (x)
  if (nargin != 1)
    print_usage ();
  endif
  y = asec (x) .* 180 ./ pi;
endfunction;

%!error(asecd())
%!error(asecd(1,2))
%!assert(asecd(0:10:90),180./pi.*asec(0:10:90),-10*eps)
