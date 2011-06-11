## -*- texinfo -*-
## @deftypefn {Function File} {@var{idx} =} dsearch (@var{x}, @var{y}, @var{tri}, @var{xi}, @var{yi})
## @deftypefnx {Function File} {@var{idx} =} dsearch (@var{x}, @var{y}, @var{tri}, @var{xi}, @var{yi}, @var{s})
## Returns the index @var{idx} or the closest point in @code{@var{x}, @var{y}}
## to the elements @code{[@var{xi}(:), @var{yi}(:)]}.  The variable @var{s} is
## accepted but ignored for compatibility.
## @seealso{dsearchn, tsearch}
## @end deftypefn

function idx = dsearch (x, y, t, xi, yi, s)
  if (nargin < 5 || nargin > 6)
    print_usage ();
  endif
  idx = __dsearchn__ ([x(:), y(:)], [xi(:), yi(:)]);
endfunction

%!shared x, y, tri
%! x = [-1;-1;1];
%! y = [-1;1;-1];
%! tri = [1,2,3]; 
%!assert (dsearch(x,y,tri,1,1/3), 3);
%!assert (dsearch(x,y,tri,1/3,1), 2);
