## -*- texinfo -*-
## @deftypefn {Mapping Function} {} asech (@var{x})
## Compute the inverse hyperbolic secant of each element of @var{x}.
## @seealso{sech}
## @end deftypefn

## Author: jwe

function w = asech (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = acosh (1 ./ z);

endfunction

%!test
%! v = [0, pi*i];
%! x = [1, -1];
%! assert(all (abs (asech (x) - v) < sqrt (eps)));

%!error asech ();

%!error asech (1, 2);

