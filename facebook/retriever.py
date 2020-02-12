import subprocess
import time
import thread
import autopy
#import threading

# IMPORTANT: USE THREAD IF YOU DON't HAVE AUTOPY


start_time = time.time()
i = 1
while i < 300:
    subprocess.call(["python", "retrieve.py"])
    subprocess.call(["python", "generate.py"])
    i += 1
end_time = (time.time() - start_time)
time_file = open("time", "w")
time_file.seek(0)
time_file.write(str(end_time))
time_file.close()

# now shutdown computer
print autopy.mouse.get_pos()
autopy.mouse.move(1345, 11)
autopy.mouse.click()
autopy.mouse.smooth_move(1161, 250)
autopy.mouse.click()
autopy.mouse.smooth_move(766, 411)
autopy.mouse.click()


"""
# spawns a child process to execute command
def execute(command):
  subprocess.call(command)
  time.sleep(2)
# Create two threads as follows
try:
   #thread.start_new_thread(execute(["sudo","shutdown","-P","now"]) )
   thread.start_new_thread(execute(["echo","thapaliya"]),1)
   autopy.key.type_string("tadada haha")
   thread.start_new_thread(execute(["echo","tada"]),1)
  #t=threading.Thread(target=run)
  #t.daemon = True  # set thread to daemon ('ok' won't be printed in this case)
  #t.start()
except Exception as ex:
   print "Error: unable to start thread"
   print ex"""
