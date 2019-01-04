[rawData SampleRate] = audioread('/Users/Isaac/Desktop/POE/wavFiles/Nocturne.wav');
TotalTime = floor(length(rawData)./SampleRate);
VectorSamples = rawData(1:SampleRate*TotalTime);
[s,f,t,p] = spectrogram(VectorSamples,256,[],[],SampleRate,'yaxis');

[q,nd] = max(10*log10(p));

hold on;
plot3(t,f(nd),q,'r','linewidth',4);
saveas(gcf,'Nocturne_LineGraph','fig');
hold off;
colorbar;
view(2);

f = openfig('Nocturne_LineGraph.fig')
H = findobj(f, 'type', 'line');
X_data = get(H, 'xdata');
Y_data = get(H, 'ydata');

fidx = fopen('/Users/Isaac/Desktop/POE/images/NocturneSpectrum/Nocturne_XData.text','wt'); 
fidy = fopen('/Users/Isaac/Desktop/POE/images/NocturneSpectrum/Nocturne_YData.text','wt'); 
fprintf(fidx,'%.4f\n',X_data);  
fprintf(fidy,'%.4f\n',Y_data);  
fclose(fidx);
fclose(fidy);
