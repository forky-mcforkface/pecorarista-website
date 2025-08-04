import qualified Data.Set as S
import Text.Pandoc.Options (Extension(Ext_east_asian_line_breaks), readerExtensions)
-- omission --
customPandocCompiler =
    let
        defaultExtensions = readerExtensions defaultHakyllReaderOptions
        newExtensions = S.insert Ext_east_asian_line_breaks defaultExtensions
        readerOptions = defaultHakyllReaderOptions { readerExtensions = newExtensions }
    in
        pandocCompilerWith readerOptions defaultHakyllWriterOptions
