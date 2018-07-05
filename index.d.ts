declare module "react-native-shortcut-badge" {

    interface ShortcutBadgeStatic {
        /** Launcher name, null if unknown */
        readonly launcher: string | null;
        /** If the launcher supported? */
        readonly supported: boolean;

        /**
         * Disable badges in Android O and above.
         */
        disableInOreo(on: boolean): void;

        /**
         * Set the badge count. Use 0 to remove it.
         */
        setCount(count: number): Promise<boolean>;

        /**
         * Get the badge count.
         */
        getCount(): number;
    }

    const ShortcutBadge: ShortcutBadgeStatic;

    export default ShortcutBadge;
}
