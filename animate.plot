if(!defined(frame) || frame==0) frame=0; dt=2; set pm3d; set view 0,0; set cbrange [] writeback; set zrange [] writeback;
splot "data.out" index frame using 1:2:3 with pm3d
#unset autoscale cb
set cbrange restore
#set zrange restore
pause dt
frame=frame+1
dt=0.05
if(frame<400) reread
pause 2
frame = 0
reread