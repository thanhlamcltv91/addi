## -*- texinfo -*-
## @deftypefn {Mapping Function} {} acoth (@var{x})
## Compute the inverse hyperbolic cotangent of each element of @var{x}.
## @seealso{coth}
## @end deftypefn

## Author: jwe

function w = acoth (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = atanh (1 ./ z);

endfunction

%!test
%! rt2 = sqrt (2);
%! rt3 = sqrt (3);
%! v = -i*[pi/6, pi/4, pi/3, -pi/3, -pi/4, -pi/6];
%! x = i*[rt3, 1, rt3/3, -rt3/3, -1, -rt3];
%! assert(all (abs (acoth (x) - v) < sqrt (eps)));

%!error acoth ();

%!error acoth (1, 2);

