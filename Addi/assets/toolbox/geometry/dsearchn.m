## -*- texinfo -*-
## @deftypefn {Function File} {@var{idx} =} dsearchn (@var{x}, @var{tri}, @var{xi})
## @deftypefnx {Function File} {@var{idx} =} dsearchn (@var{x}, @var{tri}, @var{xi}, @var{outval})
## @deftypefnx {Function File} {@var{idx} =} dsearchn (@var{x}, @var{xi})
## @deftypefnx {Function File} {[@var{idx}, @var{d}] =} dsearchn (@dots{})
## Returns the index @var{idx} or the closest point in @var{x} to the elements
## @var{xi}.  If @var{outval} is supplied, then the values of @var{xi} that are
## not contained within one of the simplicies @var{tri} are set to 
## @var{outval}.  Generally, @var{tri} is returned from @code{delaunayn 
## (@var{x})}.
## @seealso{dsearch, tsearch}
## @end deftypefn

function [idx, d] = dsearchn (x, t, xi, outval)
  if (nargin < 2 || nargin > 4)
    print_usage ();
  endif

  if (nargin == 2)
    [idx, d] = __dsearchn__ (x, t);
  else
    [idx, d] = __dsearchn__ (x, xi);
    if (nargin == 4)
      idx2 = isnan (tsearchn (x, t, xi));
      idx(idx2) = outval;
      d(idx2) = outval;
    endif
  endif
endfunction

%!shared x, tri
%! x = [-1,-1;-1,1;1,-1]; 
%! tri = [1,2,3]; 
%!assert (dsearchn(x,tri,[1,1/3]), 3);
%!assert (dsearchn(x,tri,[1,1/3],NaN), NaN);
%!assert (dsearchn(x,tri,[1,1/3],NA), NA);
%!assert (dsearchn(x,tri,[1/3,1]), 2);
%!assert (dsearchn(x,tri,[1/3,1],NaN), NaN);
%!assert (dsearchn(x,tri,[1/3,1],NA), NA);
