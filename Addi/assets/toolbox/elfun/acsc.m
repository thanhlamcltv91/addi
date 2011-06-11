## -*- texinfo -*-
## @deftypefn {Mapping Function} {} acsc (@var{x})
## Compute the inverse cosecant in radians for each element of @var{x}.
## @seealso{csc, acscd}
## @end deftypefn

## Author: jwe

function w = acsc (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = asin (1 ./ z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! v = [pi/6, pi/4, pi/3, pi/2, pi/3, pi/4, pi/6];
%! x = [2, rt2, 2*rt3/3, 1, 2*rt3/3, rt2, 2];
%! assert(all (abs (acsc (x) - v) < sqrt (eps)));

%!error acsc ();

%!error acsc (1, 2);

