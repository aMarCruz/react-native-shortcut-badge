declare module "react-native-shortcut-badge" {

    interface ShortcutBadgeStatic {
        /**
         * Launcher home name, null if unknown
         */
        readonly launcher: string | null;

        /**
         * If the launcher supported?
         */
        readonly supported: boolean;

        /**
         * Set the badge count. Use 0 to remove it.
         */
        setCount(count: number): Promise<boolean>;

        /**
         * Get the badge count.
         */
        getCount(): Promise<number>;

        /**
         * Reads the counter from the storage
         */
        requestPermission(): Promise<void>;
    }

    const ShortcutBadge: ShortcutBadgeStatic;

    export default ShortcutBadge;
}
