## -*- texinfo -*-
## @deftypefn {Mapping Function} {} asec (@var{x})
## Compute the inverse secant in radians for each element of @var{x}.
## @seealso{sec, asecd}
## @end deftypefn

## Author: jwe

function w = asec (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = acos (1 ./ z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! v = [0, pi/6, pi/4, pi/3, 2*pi/3, 3*pi/4, 5*pi/6, pi];
%! x = [1, 2*rt3/3, rt2, 2, -2, -rt2, -2*rt3/3, -1];
%! assert(all (abs (asec (x) - v) < sqrt (eps)));

%!error asec ();

%!error asec (1, 2);
