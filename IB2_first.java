/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    Resample.java
 *    Copyright (C) 2002 University of Waikato, Hamilton, New Zealand
 *
 */

package weka.filters.supervised.instance;

import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.RevisionUtils;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.filters.Filter;
import weka.filters.SupervisedFilter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

/** 
 <!-- globalinfo-start -->
 * Produces a random subsample of a dataset using either sampling with replacement or without replacement.<br/>
 * The original dataset must fit entirely in memory. The number of instances in the generated dataset may be specified. The dataset must have a nominal class attribute. If not, use the unsupervised version. The filter can be made to maintain the class distribution in the subsample, or to bias the class distribution toward a uniform distribution. When used in batch mode (i.e. in the FilteredClassifier), subsequent batches are NOT resampled.
 * <p/>
 <!-- globalinfo-end -->
 * 
 <!-- options-start -->
 * Valid options are: <p/>
 * 
 * <pre> -S &lt;num&gt;
 *  Specify the random number seed (default 1)</pre>
 * 
 * <pre> -Z &lt;num&gt;
 *  The size of the output dataset, as a percentage of
 *  the input dataset (default 100)</pre>
 * 
 * <pre> -B &lt;num&gt;
 *  Bias factor towards uniform class distribution.
 *  0 = distribution in input data -- 1 = uniform distribution.
 *  (default 0)</pre>
 * 
 * <pre> -no-replacement
 *  Disables replacement of instances
 *  (default: with replacement)</pre>
 * 
 * <pre> -V
 *  Inverts the selection - only available with '-no-replacement'.</pre>
 * 
 <!-- options-end -->
 *
 * @author Len Trigg (len@reeltwo.com)
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 5542 $ 
 */
public class NikosFirst
  extends Filter 
  implements SupervisedFilter, OptionHandler {
  
  /** for serialization. */
  static final long serialVersionUID = 7079064953548300681L;

  /** The subsample size, percent of original set, default 100%. */
  // Nikos             protected double m_SampleSizePercent = 100;
  
  /** The random number generator seed. */
  protected int m_RandomSeed = 1;
  
  /** The degree of bias towards uniform (nominal) class distribution. */
 // protected double m_BiasToUniformClass = 0;

  /** Whether to perform sampling with replacement or without. */
  //protected boolean m_NoReplacement = false;

  /** Whether to invert the selection (only if instances are drawn WITHOUT 
   * replacement).
   * @see #m_NoReplacement */
  //protected boolean m_InvertSelection = false;

  /**
   * Returns a string describing this filter.
   *
   * @return This is the first attempt to write my own filter
   * Later Goal is to construct ENN-rule algorithm as a weka filter.
   */
  public String globalInfo() {
    return 
        "This are the words to display"
        + "and that is another row";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option(
	"\tSpecify the random number seed (default 1)",
	"S", 1, "-S <num>"));

    result.addElement(new Option(
	"\tThe size of the output dataset, as a percentage of\n"
	+"\tthe input dataset (default 100)",
	"Z", 1, "-Z <num>"));

    result.addElement(new Option(
	"\tBias factor towards uniform class distribution.\n"
	+"\t0 = distribution in input data -- 1 = uniform distribution.\n"
	+"\t(default 0)",
	"B", 1, "-B <num>"));

    result.addElement(new Option(
	"\tDisables replacement of instances\n"
	+"\t(default: with replacement)",
	"no-replacement", 0, "-no-replacement"));

    result.addElement(new Option(
	"\tInverts the selection - only available with '-no-replacement'.",
	"V", 0, "-V"));

    return result.elements();
  }


  /**
   * Parses a given list of options. <p/>
   * 
   <!-- options-start -->
   * Valid options are: <p/>
   * 
   * <pre> -S &lt;num&gt;
   *  Specify the random number seed (default 1)</pre>
   * 
   * <pre> -Z &lt;num&gt;
   *  The size of the output dataset, as a percentage of
   *  the input dataset (default 100)</pre>
   * 
   * <pre> -B &lt;num&gt;
   *  Bias factor towards uniform class distribution.
   *  0 = distribution in input data -- 1 = uniform distribution.
   *  (default 0)</pre>
   * 
   * <pre> -no-replacement
   *  Disables replacement of instances
   *  (default: with replacement)</pre>
   * 
   * <pre> -V
   *  Inverts the selection - only available with '-no-replacement'.</pre>
   * 
   <!-- options-end -->
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  public void setOptions(String[] options) throws Exception {
    String	tmpStr;
    
    tmpStr = Utils.getOption('S', options);
    if (tmpStr.length() != 0)
      setRandomSeed(Integer.parseInt(tmpStr));
    else
      setRandomSeed(1);

    if (getInputFormat() != null) {
      setInputFormat(getInputFormat());
    }
  }

  
  
  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  
  
  
  
  
  public String [] getOptions() {
    Vector<String>	result;

    result = new Vector<String>();


    result.add("-S");
    result.add("" + getRandomSeed());

    
    return result.toArray(new String[result.size()]);
  }
    
  
  
  
  
  
 
  
  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for
   * displaying in the explorer/experimenter gui
   */
  public String randomSeedTipText() {
    return "Sets the random number seed for subsampling.";
  }
  
  /**
   * Gets the random number seed.
   *
   * @return the random number seed.
   */
  public int getRandomSeed() {
    return m_RandomSeed;
  }
  
  /**
   * Sets the random number seed.
   *
   * @param newSeed the new random number seed.
   */
  public void setRandomSeed(int newSeed) {
    m_RandomSeed = newSeed;
  }
    
  
  
  
  
  
  
 

  /** 
   * Returns the Capabilities of this filter.
   *
   * @return            the capabilities of this object
   * @see               Capabilities
   */
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities();
    result.disableAll();

    // attributes
    result.enableAllAttributes();
    result.enable(Capability.MISSING_VALUES);
    
    // class
    result.enable(Capability.NOMINAL_CLASS);
    
    return result;
  }
  
  
  
  
  
  
  
  
  
  /**
   * Sets the format of the input instances.
   *
   * @param instanceInfo an Instances object containing the input 
   * instance structure (any instances contained in the object are 
   * ignored - only the structure is required).
   * @return true if the outputFormat may be collected immediately
   * @throws Exception if the input format can't be set 
   * successfully
   */
  public boolean setInputFormat(Instances instanceInfo) 
       throws Exception {

    super.setInputFormat(instanceInfo);
    setOutputFormat(instanceInfo);
    return true;
  }

  
  
  
  
  
  
  
  
  /**
   * Input an instance for filtering. Filter requires all
   * training instances be read before producing output.
   *
   * @param instance the input instance
   * @return true if the filtered instance may now be
   * collected with output().
   * @throws IllegalStateException if no input structure has been defined
   */
  public boolean input(Instance instance) {

    if (getInputFormat() == null) {
      throw new IllegalStateException("No input instance format defined");
    }
    if (m_NewBatch) {
      resetQueue();
      m_NewBatch = false;
    }
    if (isFirstBatchDone()) {
      push(instance);
      return true;
    } else {
      bufferInput(instance);
      return false;
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  



  /**
   * creates the subsample without replacement.
   * 
   * @param random		the random number generator to use
   * @param origSize		the original size of the dataset
   * @param sampleSize		the size to generate
   * @param actualClasses	the number of classes found in the data
   * @param classIndices	the indices where classes start
   */
  
    





    // Create the new sample
    Random random = new Random(m_RandomSeed);

    
    
    
    public boolean batchFinished() {

        if (getInputFormat() == null) {
          throw new IllegalStateException("No input instance format defined");
        }

        if (!isFirstBatchDone()) {
          // Do the subsample, and clear the input instances.
          createSubsample();
          System.out.println("Seed is: "+ m_RandomSeed);
        }
        flushInput();

        m_NewBatch = true;
        m_FirstBatchDone = true;
        return (numPendingOutput() != 0);
      }
    
    
    
    protected void createSubsample() {
        int origSize = getInputFormat().numInstances();




        // Sort according to class attribute.
        getInputFormat().sort(getInputFormat().classIndex());
        
        // Create an index of where each class value starts
        int[] classIndices = new int [getInputFormat().numClasses() + 1];
        int currentClass = 0;
        classIndices[currentClass] = 0;
        for (int i = 0; i < getInputFormat().numInstances(); i++) {
          Instance current = getInputFormat().instance(i);
          if (current.classIsMissing()) {
    	for (int j = currentClass + 1; j < classIndices.length; j++) {
    	  classIndices[j] = i;
    	}
    	break;
          } else if (current.classValue() != currentClass) {
    	for (int j = currentClass + 1; j <= current.classValue(); j++) {
    	  classIndices[j] = i;
    	}          
    	currentClass = (int) current.classValue();
          }
        }
        if (currentClass <= getInputFormat().numClasses()) {
          for (int j = currentClass + 1; j < classIndices.length; j++) {
    	classIndices[j] = getInputFormat().numInstances();
          }
        }
        
        int actualClasses = 0;
        for (int i = 0; i < classIndices.length - 1; i++) {
          if (classIndices[i] != classIndices[i + 1]) {
    	actualClasses++;
          }
        }}


        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
  /**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 5542 $");
  }
  
  /**
   * Main method for testing this class.
   *
   * @param argv should contain arguments to the filter: 
   * use -h for help
   */
  public static void main(String [] argv) {
    runFilter(new Resample(), argv);
  }
}
