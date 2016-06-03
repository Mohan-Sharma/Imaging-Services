package com.nzion.zkoss.composer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.labels.SymbolicXYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.general.Series;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.zkoss.zul.Area;
import org.zkoss.zul.Imagemap;

import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * Feb 8, 2011
 */
public class ChartController {

	private final List<ChartPoint> points = new ArrayList<ChartPoint>();

	private final String title;

	private String xAxisLabel;

	private String yAxisLabel;

	private final Set<String> categories = new HashSet<String>(5);

	private XYDataset dataset;

	private final Map<String, Series> categorySeriesMap = new HashMap<String, Series>(5);

	private ChartPoint minimumRange;

	private ChartPoint maximumRange;

	double domainTickUnit = 10;

	public ChartController(String title) {
	this.title = title;
	}

	public ChartController(String title, String xAxisLabel, String yAxisLabel) {
	this.title = title;
	this.xAxisLabel = xAxisLabel;
	this.yAxisLabel = yAxisLabel;
	}

	public void addValue(String category, Comparable<?> xValue, Number yValue) {
	categories.add(category);
	ChartPoint newPoint = new ChartPoint(category, xValue, yValue);
	points.add(new ChartPoint(category, xValue, yValue));
	if (minimumRange == null || minimumRange.yValue.doubleValue() > yValue.doubleValue()) minimumRange = newPoint;
	if (maximumRange == null || maximumRange.yValue.doubleValue() < yValue.doubleValue()) maximumRange = newPoint;
	}

	public void addValue(String category, Number xValue, Number yValue) {
	categories.add(category);
	ChartPoint newPoint = new ChartPoint(category, (Comparable<?>) xValue, yValue);
	points.add(newPoint);
	if (minimumRange == null || minimumRange.yValue.doubleValue() > yValue.doubleValue()) minimumRange = newPoint;
	if (maximumRange == null || maximumRange.yValue.doubleValue() < yValue.doubleValue()) maximumRange = newPoint;
	}

	public void renderTimeSeriesChart(Imagemap chartarea) throws IOException {
	buildTimeSeriesDataSet();
	JFreeChart chart = ChartFactory.createTimeSeriesChart(title, xAxisLabel, yAxisLabel, UtilValidator.isNotEmpty(categories)?dataset:null, true , true ,false);
	chart.setBackgroundPaint(Color.white);
	XYPlot plot = (XYPlot) chart.getPlot();
	plot.setBackgroundPaint(Color.white);
	plot.setDomainGridlinePaint(Color.blue);
	plot.setRangeGridlinePaint(Color.blue);
	plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
	XYToolTipGenerator toolTipGenerator = new org.jfree.chart.labels.StandardXYToolTipGenerator();
	for (int i = 0; i < categories.size(); ++i) {
		renderer.setSeriesShapesFilled(i, true);
		renderer.setSeriesShapesVisible(i, true);
		renderer.setSeriesToolTipGenerator(i, toolTipGenerator);
	}
	org.jfree.chart.axis.DateAxis axis = (org.jfree.chart.axis.DateAxis) plot.getDomainAxis();
	axis.setDateFormatOverride(new java.text.SimpleDateFormat("MM-yy"));
	finishRendering(chart, chartarea);
	}

	public void renderXYSeriesChart(Imagemap chartarea) throws IOException {
	buildXYDataSet();
	/*if (VitalSign.HEIGHT.equals(yAxisLabel)) {
		yAxisLabel = yAxisLabel + " (in cm)";
	} else
		if (VitalSign.WETGHT.equals(yAxisLabel)) {
			yAxisLabel = yAxisLabel + " (in kg)";
		}*/
	JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL,
			true, true, false);
	org.jfree.chart.plot.XYPlot plot = chart.getXYPlot();
	plot.setBackgroundPaint(java.awt.Color.white);
	plot.setDomainGridlinePaint(java.awt.Color.blue);
	plot.setRangeGridlinePaint(java.awt.Color.blue);
	plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	org.jfree.chart.renderer.xy.XYSplineRenderer renderer = new XYSplineRenderer();
	renderer.setToolTipGenerator(new SymbolicXYItemLabelGenerator());
	int i =0;
    renderer.setBaseShape(new Rectangle(0,0));
	for (Iterator<String> iter = categories.iterator();iter.hasNext();) {
         renderer.setSeriesLinesVisible(i, true);
         String cat = iter.next();
         if("P5".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(0,153,102));
         }else if("P10".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(255,51,0));
         }else if("P25".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(255,102,204));
         }else if("P50".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(102,0,51));
         }else if("P75".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(255,204,204));
         }else if("P90".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(102,51,255));
         }else if("P95".equals(cat)) {
             renderer.setSeriesPaint(i,new Color(0,0,153));
         }else {
                 renderer.setSeriesStroke(i, new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                 renderer.setSeriesPaint(i,Color.black);
                 renderer.setSeriesShape(i,new Ellipse2D.Double(-2.0, -2.0, 5.0, 5.0));;
         }
         i++;
	}
	renderer.setAutoPopulateSeriesShape(false);
	renderer.setBasePaint(Color.black);

	XYSeriesLabelGenerator generator = new StandardXYSeriesLabelGenerator();
	((XYItemRenderer) renderer).setLegendItemLabelGenerator(generator);

	org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
	plot.getDomainAxis().setAutoRangeMinimumSize(12);// Range(24, 240);
	((NumberAxis) plot.getDomainAxis()).setTickUnit(new NumberTickUnit(domainTickUnit));
	rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
	rangeAxis.setFixedAutoRange(100);
	rangeAxis.setTickUnit(new NumberTickUnit(5));
	rangeAxis.configure();
	rangeAxis.setRange(Math.floor(minimumRange.yValue.doubleValue()) - 10,
			Math.ceil(maximumRange.yValue.doubleValue()) + 5);

	plot.setRangePannable(true);
	plot.setNoDataMessage("No Data Available.");
	plot.setRenderer(renderer);

	finishRendering(chart, chartarea);

	}

	public void finishRendering(JFreeChart chart, Imagemap chartarea) {
	ChartRenderingInfo info = new ChartRenderingInfo();
	java.awt.image.BufferedImage bi = chart.createBufferedImage(800, 700, java.awt.image.BufferedImage.TRANSLUCENT,
			info);
	for (Object entity : info.getEntityCollection().getEntities()) {
		if (!(entity instanceof org.jfree.chart.entity.XYItemEntity)) continue;
		XYItemEntity xyEntity = (XYItemEntity) entity;
		Area area = new Area();
		area.setParent(chartarea);
		area.setCoords(xyEntity.getShapeCoords());
		area.setShape(xyEntity.getShapeType());
		area.setTooltiptext(xyEntity.getToolTipText());
	}
	byte[] data = null;
	try {
		data = org.jfree.chart.encoders.EncoderUtil.encode(bi, org.jfree.chart.encoders.ImageFormat.PNG, true);
		org.zkoss.image.AImage img = new org.zkoss.image.AImage("Graph", data);
		chartarea.setContent(img);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

	private void buildTimeSeriesDataSet() {
	if (UtilValidator.isEmpty(categories) || UtilValidator.isEmpty(points)) return;
	dataset = new TimeSeriesCollection();
	for (String category : categories) {
		TimeSeries series = new TimeSeries(category);
		((TimeSeriesCollection) dataset).addSeries(series);
		categorySeriesMap.put(category, series);
	}
	for (ChartPoint point : points) {
		TimeSeries series = (TimeSeries) categorySeriesMap.get(point.category);
		series.add(new Second(point.getXasDateValue()), point.yValue);
	}
	}

	private void buildXYDataSet() {
	if (UtilValidator.isEmpty(categories) || UtilValidator.isEmpty(points)) return;
	List<String> sortedCategories = new ArrayList<String>(categories);
	dataset = new XYSeriesCollection();
	for (String category : sortedCategories) {
		XYSeries series = new XYSeries(category);
		((XYSeriesCollection) dataset).addSeries(series);
		categorySeriesMap.put(category, series);
	}
	for (ChartPoint point : points) {
		XYSeries series = (XYSeries) categorySeriesMap.get(point.category);
		series.add(point.getXasNumberValue(), point.yValue);
	}
	}

	public void clear() {
	points.clear();
	categories.clear();
	categorySeriesMap.clear();
	}

	private static class ChartPoint {
		String category;

		Comparable<?> xValue;

		Number yValue;

		ChartPoint(String category, Comparable<?> xValue, Number yValue) {
		this.category = category;
		this.xValue = xValue;
		this.yValue = yValue;
		}

		public Date getXasDateValue() {
		return (Date) xValue;
		}

		public Number getXasNumberValue() {
		return (Number) xValue;
		}
	}

	private static final long serialVersionUID = 1L;
}