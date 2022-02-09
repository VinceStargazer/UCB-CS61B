import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static GuitarString[] createStrings() {
        GuitarString[] strings = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            double concert = CONCERT_A * Math.pow(2, (i - 24) / 12.0);
            strings[i] = new GuitarString(concert);
        }
        return strings;
    }

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString[] strings = createStrings();

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                for (int i = 0; i < 37; i++) {
                    if (KEYBOARD.charAt(i) == key) {
                        strings[i].pluck();
                    }
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (GuitarString s : strings) {
                sample += s.sample();
                s.tic();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);
        }
    }
}
