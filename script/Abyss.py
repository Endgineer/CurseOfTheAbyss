import matplotlib.pyplot as plt
from matplotlib import colors
import numpy as np
import json

DATAFILE = '-5765714021476234612_Dev_16308'

# Load JSON data from a file
with open(f'./run/abyss_log/{DATAFILE}.json', 'r') as file:  # Replace 'data.json' with the path to your file
    data = json.load(file)

# Extract the 'data' array from the JSON structure
data_points = data['data']

# Prepare data for plotting
gt_values = [point['gt'] for point in data_points]
y_values = [point['y'] for point in data_points]
f_values = [point['f'] for point in data_points]
s_values = [point['s'] for point in data_points]
S1_values = [point['S1'] for point in data_points]
S2_values = [point['S2'] for point in data_points]
S3_values = [point['S3'] for point in data_points]
S4_values = [point['S4'] for point in data_points]
S5_values = [point['S5'] for point in data_points]
S6_values = [point['S6'] for point in data_points]

# Normalize the f values to be between 0 and 1 using matplotlib's Normalize
norm = colors.Normalize(vmin=0, vmax=1)

# Plot 1: y vs gt with color based on f
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    plt.plot(
        gt_values[i-1:i+1], 
        y_values[i-1:i+1], 
        color=plt.cm.inferno(norm(f_values[i-1])), 
        lw=2
    )
plt.ylabel('y')
plt.xlabel('gt')
plt.title('y vs gt with f color-mapping')
plt.ylim(-64, 8)
plt.yticks(np.arange(-64, 9, 8))
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-y_vs_gt.png')
plt.close()

# Plot 2: f vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    plt.plot(
        gt_values[i-1:i+1], 
        f_values[i-1:i+1], 
        color=plt.cm.inferno(norm(f_values[i-1])), 
        lw=2
    )
plt.xlabel('gt')
plt.ylabel('f')
plt.title('f vs gt')
plt.ylim(0, 1)
plt.yticks(np.arange(0, 1.1, 0.1))
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-f_vs_gt.png')
plt.close()

# Plot 3: s vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if s_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            s_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('s')
plt.title('s vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-s_vs_gt.png')
plt.close()

# Plot 4: S1 vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if S1_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            S1_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('S1')
plt.title('S1 vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-S1_vs_gt.png')
plt.close()

# Plot 5: S2 vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if S2_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            S2_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('S2')
plt.title('S2 vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-S2_vs_gt.png')
plt.close()

# Plot 6: S3 vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if S3_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            S3_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('S3')
plt.title('S3 vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-S3_vs_gt.png')
plt.close()

# Plot 7: S4 vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if S4_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            S4_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('S4')
plt.title('S4 vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-S4_vs_gt.png')
plt.close()

# Plot 8: S5 vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if S5_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            S5_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('S5')
plt.title('S5 vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-S5_vs_gt.png')
plt.close()

# Plot 9: S6 vs gt
plt.figure(figsize=(6, 6))
for i in range(1, len(gt_values)):
    if S6_values[i] > 0:  # Only plot when filtered_s_values[i] > 0
        plt.scatter(
            gt_values[i], 
            S6_values[i], 
            color=plt.cm.inferno(norm(f_values[i])), 
            s=50  # Adjust the size of the scatter points as needed
        )
plt.xlabel('gt')
plt.ylabel('S6')
plt.title('S6 vs gt')
plt.tight_layout()
plt.savefig(f'./run/abyss_log/{DATAFILE}-S6_vs_gt.png')
plt.close()
