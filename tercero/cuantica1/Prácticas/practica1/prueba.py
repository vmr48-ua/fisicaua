import sys
import time

def print_progress_bar(progress):
    bar_length = 50
    block = int(round(bar_length * progress / 100))
    progress_str = f"{progress:.2f}%"
    bar = "#" * block + "-" * (bar_length - block)
    print(f"\r[{bar}] {progress_str}",end='\r')

# Example usage:
for i in range(101):
    time.sleep(0.1)  # Simulating some task that takes time
    print_progress_bar(i)

print("\nTask complete!")