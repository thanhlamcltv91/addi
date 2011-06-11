## -*- texinfo -*-
## @deftypefn {Mapping Function} {} coth (@var{x})
## Compute the hyperbolic cotangent of each element of @var{x}.
## @seealso{acoth}
## @end deftypefn

## Author: jwe

function w = coth (z)

  if (nargin != 1)
    print_usage ();
  endif

  w = 1 ./ tanh (z);

endfunction

%!test
%! x = [pi/2*i, 3*pi/2*i];
%! v = [0, 0];
%! assert(all (abs (coth (x) - v) < sqrt (eps)));

%!error coth ();

%!error coth (1, 2);

