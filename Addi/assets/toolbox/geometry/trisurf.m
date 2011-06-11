## -*- texinfo -*-
## @deftypefn {Function File} {} trisurf (@var{tri}, @var{x}, @var{y}, @var{z})
## @deftypefnx {Function File} {@var{h} =} trisurf (@dots{})
## Plot a triangular surface in 3D.  The variable @var{tri} is the triangular
## meshing of the points @code{(@var{x}, @var{y})} which is returned 
## from @code{delaunay}.  The variable @var{z} is value at the point 
## @code{(@var{x}, @var{y})}.  The output argument @var{h} is the graphic 
## handle to the plot.
## @seealso{triplot, delaunay3}
## @end deftypefn

function h = trisurf (tri, x, y, z, varargin)

  if (nargin < 3)
    print_usage ();
  endif

  if (nargin == 3)
    triplot (tri, x, y);
  elseif (ischar (z))
    triplot (tri, x, y, z, varargin{:});
  else
    if (nargin > 4 && isnumeric (varargin{1}))
      c = varargin{1};
      varargin(1) = [];
    else
      c = z;
    endif

    newplot ();
    if (nargout > 0)
      h = patch ("Faces", tri, "Vertices", [x(:), y(:), z(:)],  
	     "FaceVertexCData", reshape (c, numel (c), 1), 
	     "FaceColor", "flat", "EdgeColor", "none",
	     varargin{:});
    else
      patch ("Faces", tri, "Vertices", [x(:), y(:), z(:)],  
	     "FaceVertexCData", reshape (c, numel (c), 1), 
	     "FaceColor", "flat", "EdgeColor", "none",
	     varargin{:});
    endif

    if (! ishold ())
      set (gca(), "view", [-37.5, 30],
	   "xgrid", "on", "ygrid", "on", "zgrid", "on");
    endif
  endif
endfunction

%!demo
%! N = 10;
%! rand ('state', 10)
%! x = 3 - 6 * rand (N, N);
%! y = 3 - 6 * rand (N, N);
%! z = peaks (x, y);
%! tri = delaunay (x(:), y(:));
%! trisurf (tri, x(:), y(:), z(:));
