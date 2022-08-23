import java.util.ArrayList;

/**
 * The class Neuron simplents a interneuron for the class Network.
 * 
 * @author Vera Röhr
 * @version 1.0
 * @since 2019-01-11
 */
public class Interneuron extends Neuron {
	/**
	 * {@inheritDoc}
	 */

	public Interneuron(int index) {
		// TODO
		super(index);
		this.outgoingsynapses = new ArrayList<Synapse>() ;
	}

	/**
	 * Divides incoming signal into equal parts for all the outgoing synapses
	 * Transmits the parts via Synapse.transmit(Double[]);
	 * 
	 * @param signal 3 dimensional signal from another neuron
	 * @return 3 dimensional neural signal, which got transmitted to the synapses (for testing.)
	 */
	@Override
	public double[] integrateSignal(double[] signal) {
		// TODO
		/*Die Methode bekommt ein synaptisches Signal und verteilt es auf alle nachgeschalteten
		Neuronen (siehe Photoreceptor.integrateSignal und Synapse.transmit). Damit
		sich das Signal im Netzwerk nicht mit der Anzahl der Synapsen potenziert, wird das Signal zu
		gleichen Teilen auf die ausgehenden Synapsen aufgeteilt. */


        for(int i = 0 ; i<3 ;i++){
			signal[i] = signal[i] / outgoingsynapses.size ();
		}
        for(int i = 0 ;i<outgoingsynapses.size () ; i++) {
			outgoingsynapses.get (i).transmit (signal);
		}
		return signal;
	}
}


