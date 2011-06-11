## -*- texinfo -*-
## @deftypefn {Mapping Function} {} acot (@var{x})
## Compute the inverse cotangent in radians for each element of @var{x}.
## @seealso{cot, acotd}
## @end deftypefn

## Author: jwe

function w = acot (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = atan (1./z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! x = [rt3, 1, rt3/3, 0, -rt3/3, -1, -rt3];
%! v = [pi/6, pi/4, pi/3, pi/2, -pi/3, -pi/4, -pi/6];
%! assert(all (abs (acot (x) - v) < sqrt (eps)));

%!error acot ();

%!error acot (1, 2);

