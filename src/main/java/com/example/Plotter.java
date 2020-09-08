package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * Plotting class implemented to generate graphs from the AudioFeature data using JFreeChart.
 */
public class Plotter {
  /**
   * Creates a histogram using Integer variables from the AudioFeatures.
   *
   * @param audioFeatures List of AudioFeatures to get data from
   * @param fieldGetter Getter method to call from audioFeatures
   * @param name String title of plot
   */
  public static void generateHistogramFromIntegers(
      List<AudioFeature> audioFeatures, Function<AudioFeature, Integer> fieldGetter, String name) {
    double[] values = new double[audioFeatures.size()];

    for (int i = 0; i < audioFeatures.size(); i++) {
      // Casts the Integer as a double
      values[i] = (double) fieldGetter.apply(audioFeatures.get(i)).intValue();
    }

    createPlot(name, values);
  }

  /**
   * Creates a histogram using Double variables from the AudioFeatures.
   *
   * @param audioFeatures List of AudioFeatures to get data from
   * @param fieldGetter Getter method to call from audioFeatures
   * @param name String title of plot
   */
  public static void generateHistogramFromDoubles(
      List<AudioFeature> audioFeatures, Function<AudioFeature, Double> fieldGetter, String name) {
    double[] values = new double[audioFeatures.size()];

    for (int i = 0; i < audioFeatures.size(); i++) {
      // Casts the Double as a double
      values[i] = fieldGetter.apply(audioFeatures.get(i)).doubleValue();
    }

    createPlot(name, values);
  }

  /**
   * Plots the histogram using the data generated from previous methods.
   *
   * @param name String title of plot
   * @param values double[] containing histogram data
   */
  private static void createPlot(String name, double[] values) {
    HistogramDataset dataset = new HistogramDataset();
    dataset.addSeries(name, values, 10);  // Apply the data to the graph

    JFreeChart histogram =
        ChartFactory.createHistogram(name + " Histogram", " Values", name + " Frequency", dataset);

    // Use the name provided as file name.png
    try {
      ChartUtils.saveChartAsPNG(new File("src/main/plots/" + name + ".png"), histogram, 800, 800);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
